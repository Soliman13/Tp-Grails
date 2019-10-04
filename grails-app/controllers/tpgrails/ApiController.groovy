package tpgrails

import grails.converters.JSON
import grails.converters.XML
import org.apache.tools.ant.types.FileList

class ApiController {

    AnnonceService annonceService

    def annonce(){
        switch(request.getMethod()) {
            case "GET":
                if (!params.id) {
                    return response.status = 400
                }
                def annonceInstance= Annonce.get(params.id)
                if (!annonceInstance)
                    return response.status= 404
                response.withFormat{
                    xml{ render annonceInstance as XML}
                    json{ render annonceInstance as JSON }
                }
                break
            case "PUT":

            case "PATCH":
                break
            case "DELETE":
                if (!params.id) {
                    return response.status = 400
                }
                def annonceInstance= Annonce.get(params.id)
                if (!annonceInstance)
                    return response.status= 404
                annonceInstance.delete(flush: true)
                return response.status= 200
                break
            default:
                return response.status= 405
                break}
        return response.status= 406
    }

    def annonces(){
        switch(request.getMethod()) {
            case "GET":
                def annonceInstance= Annonce.list()
                if (!annonceInstance)
                    return response.status= 404
                response.withFormat{
                    xml{ render annonceInstance as XML}
                    json{ render annonceInstance as JSON }
                }
                break
            case "POST":
                def type = request.getHeader("Accept")
                def body
                if(type.contains("XML")){
                    body = request.XML
                }
                else {
                    body = request.JSON
                }

                def file = request.getFile("illustration")
                def author = User.get(params.author)
                if(!author || !file)
                    return response.status = 400

                def imageName = file.originalFilename
                file.transferTo(new File(grailsAppapplication.config.maconfig.asset_path + imageName))

                def annonceInstance = new Annonce(title: params.get("title"), description: params.get("description"),
                       validTill: params.get("validTill"), state: params.get("state"))

                annonceInstance.addToIllustrations(new Illustration(filename: imageName))
                annonceInstance.author = author
                annonceService.save(annonceInstance)

                return response.status = 200
                break
            default:
                return response.status= 405
                break}
        return response.status= 406
    }
}