package com.store.api.controller

import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.nio.file.Paths

@RestController
@RequestMapping("/uploads")
class FileController {

    @GetMapping("/{filename:.+}")
    fun serveFile(@PathVariable filename: String): ResponseEntity<Resource> {
        val file = Paths.get("src/main/resources/static/uploads").resolve(filename).normalize()
        val resource = UrlResource(file.toUri())
        return if (resource.exists() || resource.isReadable) {
            ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"$filename\"")
                    .body(resource)
            } else {
                ResponseEntity.notFound().build()
            }
    }
}
