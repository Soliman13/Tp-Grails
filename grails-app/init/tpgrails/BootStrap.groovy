package tpgrails

class BootStrap {

    def init = { servletContext ->
        def userInstance = new User(username: "totorino", password: "password", thumbnail: new Illustration(filename: "thumb.png"))
                .save(flush: true, failOnError: true)

        (1..5).each {
            userInstance.addToAnnonces(
                    new Annonce(
                            title: "title",
                            description: "description",
                            validTill: new Date(),
                            state: Boolean.TRUE
                    )
                            .addToIllustrations(new Illustration(filename: "filename"))
                            .addToIllustrations(new Illustration(filename: "filename2"))
                            .addToIllustrations(new Illustration(filename: "filename3"))
            )
            userInstance.save(flush: true, failOnError: true)
        }
    }
    def destroy = {
    }
}
