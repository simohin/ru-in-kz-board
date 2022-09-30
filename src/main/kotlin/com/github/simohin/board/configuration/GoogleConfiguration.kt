package com.github.simohin.board.configuration

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import com.helger.commons.base64.Base64
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.util.Collections

@Configuration
class GoogleConfiguration(
    @Value("\${google.auth.tokens.dir:tokens}")
    private val tokensDir: String,
    @Value("\${google.auth.appName.dir:BoardApplication}")
    private val appName: String,
    @Value("\${google.auth.credentials.file.encoded:}")
    private val credentialsFileEncoded: String
) {

    @Bean
    fun scopes(): MutableCollection<String> = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY)

    @Bean
    fun gsonFactory(): GsonFactory = GsonFactory.getDefaultInstance()

    @Bean
    fun netHttpTransport(): NetHttpTransport = GoogleNetHttpTransport.newTrustedTransport()

    @Bean
    @Scope(scopeName = "prototype")
    fun credentialsFileStream(): InputStream = ByteArrayInputStream(Base64.decode(credentialsFileEncoded))

    @Bean
    fun credential(credentialsFileStream: InputStream, scopes: MutableCollection<String>): Credential =
        GoogleCredential.fromStream(credentialsFileStream).createScoped(scopes)

    @Bean
    fun sheets(
        netHttpTransport: NetHttpTransport,
        gsonFactory: GsonFactory,
        credential: Credential
    ): Sheets = Sheets.Builder(netHttpTransport, gsonFactory, credential).apply {
        applicationName = appName
    }.build()
}
