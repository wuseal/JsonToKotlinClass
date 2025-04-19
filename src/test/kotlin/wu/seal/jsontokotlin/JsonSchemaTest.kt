package wu.seal.jsontokotlin

import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.test.TestConfig
import com.google.gson.annotations.SerializedName

class JsonSchemaTest {

  @Before
  fun before() {
    TestConfig.setToTestInitState()
  }

  @Test
  fun testBasicJsonSchema() {
    val json = """{
  "${"$"}schema": "http://json-schema.org/draft-04/schema#",
  "title": "Product",
  "description": "A product from Acme\u0027s catalog",
  "type": "object",
  "properties": {
    "id": {
      "description": "The unique identifier for a product",
      "type": "integer"
    },
    "name": {
      "description": "Name of the product",
      "type": "string"
    },
    "price": {
      "type": "number",
      "minimum": 0,
      "exclusiveMinimum": true
    },
    "nested": {
      "type": "object",
      "properties": {
        "id": {
          "description": "The unique identifier for a product",
          "type": "integer"
        },
        "name": {
          "description": "Name of the product",
          "type": "string"
        },
        "price": {
          "type": "number",
          "minimum": 0,
          "exclusiveMinimum": true
        }
      },
      "required": ["id", "name"]
    }
  },
  "required": [
    "id",
    "name",
    "price"
  ]
}""".trimIndent()
    val s = json.generateKotlinClassCode("TestData")
    println(s)
  }
  
  @Test
  fun testJsonSchemaWithDefs() {
    val json = """{
  "${"$"}schema": "http://json-schema.org/draft-2020-12/schema",
  "type": "object",
  "title": "Payment",
  "description": "A payment instruction with transaction details",
  "properties": {
    "paymentId": {
      "type": "string",
      "description": "Unique identifier for the payment"
    },
    "amount": {
      "type": "number",
      "description": "Payment amount"
    },
    "currency": {
      "type": "string",
      "description": "Payment currency code"
    },
    "transaction": {
      "${"$"}ref": "#/${"$"}defs/Transaction"
    }
  },
  "required": ["paymentId", "amount", "currency", "transaction"],
  "${"$"}defs": {
    "Transaction": {
      "type": "object",
      "properties": {
        "transactionId": {
          "type": "string",
          "description": "Unique identifier for the transaction"
        },
        "status": {
          "type": "string",
          "enum": ["PENDING", "COMPLETED", "FAILED"],
          "description": "Current status of the transaction"
        },
        "details": {
          "${"$"}ref": "#/${"$"}defs/TransactionDetails"
        }
      },
      "required": ["transactionId", "status"]
    },
    "TransactionDetails": {
      "type": "object",
      "properties": {
        "createdAt": {
          "type": "string",
          "format": "date-time",
          "description": "Transaction creation timestamp"
        },
        "updatedAt": {
          "type": "string",
          "format": "date-time",
          "description": "Transaction last update timestamp"
        },
        "notes": {
          "type": "string",
          "description": "Additional notes about the transaction"
        }
      }
    }
  }
}""".trimIndent()
    
    val expected = """/**
 * A payment instruction with transaction details
 */
data class Payment(
    /**
     * Payment amount
     */
    @SerializedName("amount")
    val amount: Double = 0.0,
    /**
     * Payment currency code
     */
    @SerializedName("currency")
    val currency: String = "",
    /**
     * Unique identifier for the payment
     */
    @SerializedName("paymentId")
    val paymentId: String = "",
    @SerializedName("transaction")
    val transaction: Transaction = Transaction()
) {
    data class Transaction(
        @SerializedName("details")
        val details: TransactionDetails = TransactionDetails(),
        /**
         * Current status of the transaction
         */
        @SerializedName("status")
        val status: Status = Status(),
        /**
         * Unique identifier for the transaction
         */
        @SerializedName("transactionId")
        val transactionId: String = ""
    ) {
        data class TransactionDetails(
            /**
             * Transaction creation timestamp
             */
            @SerializedName("createdAt")
            val createdAt: java.time.OffsetDateTime = OffsetDateTime(),
            /**
             * Additional notes about the transaction
             */
            @SerializedName("notes")
            val notes: String = "",
            /**
             * Transaction last update timestamp
             */
            @SerializedName("updatedAt")
            val updatedAt: java.time.OffsetDateTime = OffsetDateTime()
        )

        /**
         * Current status of the transaction
         */
        enum class Status(val value: String) {
            PENDING("PENDING"),

            COMPLETED("COMPLETED"),

            FAILED("FAILED");
        }
    }
}""".trimIndent()
    
    val result = json.generateKotlinClassCode("Payment")
    println(result)
    
    // Use assertion to verify that the generated code matches the expected result
    assert(result.trim() == expected) { "Generated code does not match expected output" }
  }
  
  @Test
  fun testComplexJsonSchemaWithDefs() {
    // This test simulates the case from the crash report involving #/$defs/CreditTransferTransaction39
    val json = """{
  "${"$"}schema": "http://json-schema.org/draft-2020-12/schema",
  "title": "PaymentInstruction",
  "type": "object",
  "properties": {
    "msgId": {
      "type": "string",
      "description": "Point to point reference assigned by the instructing party to unambiguously identify the instruction"
    },
    "pmtInf": {
      "type": "array",
      "items": {
        "${"$"}ref": "#/${"$"}defs/PaymentInformation"
      }
    }
  },
  "required": ["msgId", "pmtInf"],
  "${"$"}defs": {
    "PaymentInformation": {
      "type": "object",
      "properties": {
        "pmtInfId": {
          "type": "string",
          "description": "Unique identification assigned by the sending party to unambiguously identify the payment information group"
        },
        "pmtMtd": {
          "type": "string",
          "description": "Specifies the means of payment that will be used to move the amount of money"
        },
        "cdtTrfTxInf": {
          "type": "array",
          "items": {
            "${"$"}ref": "#/${"$"}defs/CreditTransferTransaction"
          }
        }
      },
      "required": ["pmtInfId", "pmtMtd"]
    },
    "CreditTransferTransaction": {
      "type": "object",
      "properties": {
        "pmtId": {
          "type": "object",
          "properties": {
            "endToEndId": {
              "type": "string",
              "description": "Unique identification assigned by the initiating party to unambiguously identify the transaction"
            },
            "txId": {
              "type": "string",
              "description": "Unique identification assigned by the first instructing agent to unambiguously identify the transaction"
            }
          },
          "required": ["endToEndId"]
        },
        "amt": {
          "${"$"}ref": "#/${"$"}defs/ActiveCurrencyAndAmount"
        },
        "cdtr": {
          "${"$"}ref": "#/${"$"}defs/PartyIdentification"
        },
        "cdtrAcct": {
          "${"$"}ref": "#/${"$"}defs/CashAccount"
        }
      },
      "required": ["pmtId", "amt", "cdtr", "cdtrAcct"]
    },
    "ActiveCurrencyAndAmount": {
      "type": "object",
      "properties": {
        "ccy": {
          "type": "string",
          "description": "Medium of exchange of value, such as USD or EUR"
        },
        "value": {
          "type": "number",
          "description": "Amount of money to be moved between the debtor and creditor"
        }
      },
      "required": ["ccy", "value"]
    },
    "PartyIdentification": {
      "type": "object",
      "properties": {
        "nm": {
          "type": "string",
          "description": "Name by which a party is known"
        },
        "ctctDtls": {
          "type": "object",
          "properties": {
            "emailAdr": {
              "type": "string",
              "description": "Email address of the contact"
            },
            "phneNb": {
              "type": "string",
              "description": "Phone number of the contact"
            }
          }
        }
      },
      "required": ["nm"]
    },
    "CashAccount": {
      "type": "object",
      "properties": {
        "id": {
          "type": "object",
          "properties": {
            "iban": {
              "type": "string",
              "description": "International Bank Account Number (IBAN)"
            },
            "othr": {
              "type": "object",
              "properties": {
                "id": {
                  "type": "string",
                  "description": "Other account identification"
                }
              },
              "required": ["id"]
            }
          }
        },
        "tp": {
          "type": "object",
          "properties": {
            "cd": {
              "type": "string",
              "description": "Code specifying the nature or use of the account"
            }
          }
        },
        "ccy": {
          "type": "string",
          "description": "Identification of the currency in which the account is held"
        }
      },
      "required": ["id"]
    }
  }
}""".trimIndent()

    // Execute the code generation - if it doesn't throw an exception, our fix works
    val result = json.generateKotlinClassCode("PaymentInstruction")
    println(result)
    
    // Verify some basic expectations about the output
    assert(result.contains("data class PaymentInstruction")) { "Missing PaymentInstruction class" }
    assert(result.contains("data class PaymentInformation")) { "Missing PaymentInformation class" }
    assert(result.contains("data class CreditTransferTransaction")) { "Missing CreditTransferTransaction class" }
    assert(result.contains("data class ActiveCurrencyAndAmount")) { "Missing ActiveCurrencyAndAmount class" }
    assert(result.contains("data class PartyIdentification")) { "Missing PartyIdentification class" }
    assert(result.contains("data class CashAccount")) { "Missing CashAccount class" }
  }
}
