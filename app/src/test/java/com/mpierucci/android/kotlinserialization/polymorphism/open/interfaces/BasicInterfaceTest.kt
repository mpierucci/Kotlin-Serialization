package com.mpierucci.android.kotlinserialization.polymorphism.open.interfaces

import com.google.common.truth.Truth
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Ignore
import org.junit.Test

class BasicInterfaceTest {
    @Test
    fun `deserialize box`() {

        val deliverable = """
        {
            "type": "box",
            "address": "far",
            "recipientName": "Marco"
        }
        """.trimIndent()

        val box = Box("far", "Marco")

        val result = Json { serializersModule = module }.decodeFromString<Deliverable>(deliverable)

        Truth.assertThat(result).isEqualTo(box)

    }

    @Test
    fun `deserialize parcel`() {

        val deliverable = """
        {
            "type":"parcel",
            "senderNumber": "5",
            "recipientName": "Marco"
        }
        """.trimIndent()

        val parcel = Parcel(5, "Marco")

        val result = Json { serializersModule = module }.decodeFromString<Deliverable>(deliverable)

        Truth.assertThat(result).isEqualTo(parcel)
    }

    @Test
    @Ignore("Won't work, added specifically to  mark a point")
    fun `deserialize parcel using implementation class`() {

        val deliverable = """
        {
            "type":"parcel",
            "senderNumber": "5",
            "recipientName": "Marco"
        }
        """.trimIndent()

        val parcel = Parcel(5, "Marco")

        val result = Json { serializersModule = module }.decodeFromString<Parcel>(deliverable)

        Truth.assertThat(result).isEqualTo(parcel)
    }

    /*
        TODO IMPORTANT
        note that if we want the discriminator ("type") to be present, you actual need to submit a
        Delivery rather than a implementation (Box or Parcel)
     */

    @Test
    fun `serialize box`() {

        val box: Deliverable = Box("far", "Marco")

        val deliverable = """
        {"type":"box","address":"far","recipientName":"Marco"}
        """.trimIndent()

        val result = Json { serializersModule = module }.encodeToString(box)

        Truth.assertThat(result).isEqualTo(deliverable)

    }

    @Test
    fun `serialize parcel`() {

        val parcel: Deliverable = Parcel(5, "Marco")

        val deliverable = """
        {"type":"parcel","senderNumber":5,"recipientName":"Marco"}
        """.trimIndent()

        val result = Json { serializersModule = module }.encodeToString(parcel)

        Truth.assertThat(result).isEqualTo(deliverable)
    }

    @Test
    @Ignore("Won't work, added specifically to  mark a point")
    fun `serialize parcel using implementation class`() {

        val parcel = Parcel(5, "Marco")

        val deliverable = """
        {"type":"parcel","senderNumber":5,"recipientName":"Marco"}
        """.trimIndent()

        val result = Json { serializersModule = module }.encodeToString(parcel)

        Truth.assertThat(result).isEqualTo(deliverable)
    }


    ///////////// DIFFERENT DISCRIMINATOR ZONE //////////

    @Test
    fun `deserialize box with different discriminator`() {

        val deliverable = """
        {
            "child": "box",
            "address": "far",
            "recipientName": "Marco"
        }
        """.trimIndent()

        val box = Box("far", "Marco")

        val result = Json {
            serializersModule = module
            classDiscriminator = "child"
        }.decodeFromString<Deliverable>(deliverable)

        Truth.assertThat(result).isEqualTo(box)

    }

    @Test
    fun `serialize parcel with different discriminator`() {

        val parcel: Deliverable = Parcel(5, "Marco")

        val deliverable = """
        {"child":"parcel","senderNumber":5,"recipientName":"Marco"}
        """.trimIndent()

        val result = Json {
            serializersModule = module
            classDiscriminator = "child"
        }.encodeToString(parcel)

        Truth.assertThat(result).isEqualTo(deliverable)
    }
}