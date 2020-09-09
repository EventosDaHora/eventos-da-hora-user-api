# README - eventos-da-hora-user-api #

### Setup inicial para subir a aplicação ###

#### Ao subir a aplicação, a mesma estará disponível na porta 8181
#### Sua documentação pode ser acessada em http://localhost:8181/swagger-ui.html

* Configurações de banco de dados:
    - spring.datasource.url=jdbc:postgresql://localhost:5432/eventosdahora_user_db
    - spring.datasource.username=eventosdahora_user
    - spring.datasource.password=eventosdahora@2020


` 
Antes de subir a  aplicação deve-se criar o banco de dados e o usuário como relatado nas configurações acima.
 `

* Configurações do servidor de email:
    - spring.mail.host=smtp.gmail.com
    - spring.mail.port=587
    - spring.mail.username=emaileventosdahora@gmail.com
    - spring.mail.password=eventosdahora@2020

* Outras configurações do sistema:

    - jwt.expiration=28800000
       `Trata do tempo de expiração do token`
       
    - jwt.secret=5034618516765b232cdd17354d251d58695dbc98
        `trata da secret para a geração do token`

    - url.microservice.image=https://psgtechnology.com.br/bmc-api/file/
        `trata da url do servidor de imagem`
        
    - url.front.admin=http://localhost:4300
        `trata da url do front-end de administração`
        
    - url.front.user=http://localhost:4200
         `trata da url do front-end de usuario`
            
    - id.logo=be7eac7b-23ef-41d7-bb1f-5caa194ef9b6jwt.expiration
        `Trata do id da logo do sistema, essa logo está armazenada no servidor de imagem`
        
    - email.user.admin=emaileventosdahora@gmail.com
        `email de usuário default, usuario MASTER. Não é possível deletar via endpoint.`


