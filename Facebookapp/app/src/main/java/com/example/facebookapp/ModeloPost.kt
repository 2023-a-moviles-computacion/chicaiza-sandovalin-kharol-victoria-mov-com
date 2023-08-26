package com.example.facebookapp

class ModeloPost(
    public var id: Int?,
    public var likes: Int?,
    public var comentarios: Int?,
    public var propic: Int?,
    public var postpic: Int?,
    public var nombre: String?,
    public var tiempo: String?,
    public var estado: String?,
) {
    override fun toString(): String {
        return "${likes} - ${comentarios} - ${propic} - ${postpic} - ${nombre} - ${tiempo} - ${estado}"
    }
}