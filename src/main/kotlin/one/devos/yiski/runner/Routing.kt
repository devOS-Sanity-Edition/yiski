package one.devos.yiski.runner

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("hai hai?!?1 i guess you found me, oh s-siwwy you?!?1 w-weww don't mind me, im just a bot... existing... bweathing... sighing. teww the *boops your nose* devs *starts twerking* i said hai?!?1 :3")
        }
    }
}