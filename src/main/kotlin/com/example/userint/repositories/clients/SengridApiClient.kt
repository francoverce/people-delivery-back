package com.example.userint.repositories.clients
import com.sendgrid.Method
import com.sendgrid.Request
import com.sendgrid.SendGrid
import com.sendgrid.helpers.mail.Mail
import com.sendgrid.helpers.mail.objects.Email
import com.sendgrid.helpers.mail.objects.Personalization
import org.springframework.stereotype.Repository
import java.io.IOException

@Repository
class SengridApiClient {

    val SENDGRID_API_KEY = "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"
    val TEMPLATE_OTP = "d-2ce0b0e8b4a44636b2f96499266c8caf"
    val TEMPLATE_OTP_SUCCESS = "d-b64b87290879418a8e113dd7c54b2b9a"

    fun postOTPEmail(email: String, otp: String) {
        val from = Email("peopleDelivery@outlook.com")
        val to = Email(email)
        val personalization = Personalization()
        personalization.addBcc(from)
        personalization.addTo(to)
        val map = HashMap<String, String>()
        map["otp_code"] = otp
        personalization.addDynamicTemplateData("data", map)
        val mail = Mail()
        mail.setFrom(from)
        mail.setReplyTo(to)
        mail.setTemplateId(TEMPLATE_OTP)
        mail.addPersonalization(personalization)
        val sg = SendGrid(SENDGRID_API_KEY)
        val request = Request()
        try {
            request.method = Method.POST
            request.endpoint = "mail/send"
            request.body = mail.build()
            val response = sg.api(request)
            println(response.statusCode)
            println(response.body)
            println(response.headers)
        } catch (ex: IOException) {
            throw ex
        }
    }

    fun postOTPEmailSucces(email: String, otp: String) {
        val from = Email("peopleDelivery@outlook.com")
        val to = Email(email)
        val personalization = Personalization()
        personalization.addBcc(from)
        personalization.addTo(to)
        val map = HashMap<String, String>()
        map["new_password"] = otp
        personalization.addDynamicTemplateData("data", map)
        val mail = Mail()
        mail.setFrom(from)
        mail.setReplyTo(to)
        mail.setTemplateId(TEMPLATE_OTP_SUCCESS)
        mail.addPersonalization(personalization)
        val sg = SendGrid(SENDGRID_API_KEY)
        val request = Request()
        try {
            request.method = Method.POST
            request.endpoint = "mail/send"
            request.body = mail.build()
            val response = sg.api(request)
            println(response.statusCode)
            println(response.body)
            println(response.headers)
        } catch (ex: IOException) {
            throw ex
        }
    }
}
