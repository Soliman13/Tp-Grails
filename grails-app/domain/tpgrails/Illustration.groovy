package tpgrails

class Illustration {

    String filename

    static constraints = {
        filename blank: false, nullable: false
    }
}
