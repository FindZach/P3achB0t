package com.p3achb0t.api.interfaces

interface NetSocket : AbstractSocket {
    fun getArray(): ByteArray
    fun getExceptionWriting(): Boolean
    fun getInputStream(): Any
    fun getIsClosed(): Boolean
    fun getOutputStream(): Any
    fun getSocket(): Any
    fun getTask(): Task
    fun getTaskHandler(): TaskHandler
    fun get__c(): Int
    fun get__u(): Int
    fun get__e(): Int
    fun get__o(): Int
}
