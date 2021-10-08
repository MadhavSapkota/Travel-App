package com.app.travel.apputlis.rules
import com.wajahatkarim3.easyvalidation.core.rules.BaseRule

/*This class is responsible to set rules for firstname validation for easyvalidation library */
class ValidationRule : BaseRule {
    val regExpn=("\"[a-zA-z]+([ '-][a-zA-Z]+)*")

    override fun validate(text: String): Boolean {
        return text.contains(regExpn)
    }

    override fun getErrorMessage(): String {
        return "Invalid username"
    }
}