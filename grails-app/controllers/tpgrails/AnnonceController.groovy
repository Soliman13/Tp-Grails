package tpgrails

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class AnnonceController {

    AnnonceService annonceService
    IllustrationService illustrationService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond annonceService.list(params), model:[annonceCount: annonceService.count()]
    }

    def show(Long id) {
        respond annonceService.get(id)
    }
    def showImage(Long id) {
        respond illustrationService.get(id)
    }
    def create() {
        respond new Annonce(params)
    }

    // code non utilisé
    def uploadImage(){
        def file=request.getFile('image')
        String imageUploadPath=grailsApplication.config.imageUpload.path
        try{
            if(file && !file.empty){
                file.transferTo(new File("${imageUploadPath}/${file.name}"))
                flash.message="Image enregistrée"
            }
            else{
                flash.message="Image non enregistrée"
            }
        }
        catch(Exception e){
            log.error("Une erreur est survenue lors de l'upload de l'image",e)
        }

    }

    def save(Annonce annonce) {

        println params
        if (annonce == null) {
            notFound()
            return
        }

        //  def fichier = params.get("illustrations")
        def file = request.getFile("file")
        //générer un nom de fichier aléatoire et vérifier qu'il n'existe pas

        // on garde le fichier sur le site avec le path renseigné dans le fichier de config
        println grailsApplication.config.maconfig.asset_path + params.title
        file.transferTo(new File(grailsApplication.config.maconfig.asset_path + params.title))
        // garder une trace sur le nom du fichier
        annonce.addToIllustrations(new Illustration(filename: params.title))

        try {
            annonceService.save(annonce)
        } catch (ValidationException e) {
            respond annonce.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'annonce.label', default: 'Annonce'), annonce.id])
                redirect annonce
            }
            '*' { respond annonce, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond annonceService.get(id)
    }

    def update(Annonce annonce) {
        if (annonce == null) {
            notFound()
            return
        }

        try {
            annonceService.save(annonce)
        } catch (ValidationException e) {
            respond annonce.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'annonce.label', default: 'Annonce'), annonce.id])
                redirect annonce
            }
            '*'{ respond annonce, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        annonceService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'annonce.label', default: 'Annonce'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'annonce.label', default: 'Annonce'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
