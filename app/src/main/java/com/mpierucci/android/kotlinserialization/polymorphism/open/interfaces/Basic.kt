package com.mpierucci.android.kotlinserialization.polymorphism.open.interfaces

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass


/*
    We cannot mark an interface itself as @Serializable. But interfaces cannot be instantiated by
    themselves, they are used in kotlin to enable polymorphism. ll interfaces are considered to be
    implicitly serializable with the PolymorphicSerializer strategy. We just need to mark their
    implementing classes as @Serializable and register them  ( on the module like regular sub classes) .
 */

interface Deliverable {
    val recipientName: String
}

@Serializable
/*
    Serial name allow you to change the discriminator value to be used in polymorphic serialization
 */
@SerialName("box")
data class Box(val address: String, override val recipientName: String) : Deliverable

@Serializable
@SerialName("parcel")
data class Parcel(val senderNumber: Int, override val recipientName: String) : Deliverable

val module = SerializersModule {
    polymorphic(Deliverable::class) {
        subclass(Box::class)
        subclass(Parcel::class)
    }
}

/*
    TODO note that for this to work we need to actually work with the interface (Delivery) rather
    than the implementations (Box or Parcel) more on BasicInterfaceTest.kt
 */


/*
    By default discriminator key used for polymorphism is "type", but we can change that on json
    builder
 */

