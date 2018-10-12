package com.sterlingng.paylite.utils

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class ExtensionsKtTest : StringSpec({

    "sentence case: empty string should return empty string" {
        "".toSentenceCase() shouldBe ""
    }

    "sentence case: full name should be equal" {
        "RAYMOND TUKPE".toSentenceCase() shouldBe "Raymond Tukpe"
    }

    "sentence case: full name with trailing space should be equal" {
        "raymond tukpe ".toSentenceCase() shouldBe "Raymond Tukpe"
    }

    "initials: full name with middle name should be equal" {
        "Raymond Tukpe Jirevwe".initials() shouldBe "RT"
    }

    "initials: full name should be equal" {
        "Raymond Tukpe".initials() shouldBe "RT"
    }

    "initials: empty string should return empty string" {
        "".initials() shouldBe ""
    }

    "initials: first name should be equal" {
        "Raymond".initials() shouldBe "R"
    }
})