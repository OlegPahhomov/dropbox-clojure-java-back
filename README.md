# dropbox-clojure-java-back

####### Goal
Creating a simple file upload and viewing platform using different languages and frameworks

####### Clojure Java Back

To run:
* 1) You will need leintingen, Postgres db and front-end project
* 2) Navigate cmd to project folder. Run "lein install".
* 3) Configure datasource in project.clj under ":flyway" and java.config.AppDataSource
* 4) To fill in database run "lein flyway migrate", if you need to do clean-up "lein flyway clean". If it doesn't work, go back to 3.
* 5) Start server. "lein ring server" (it will run on 3000), you can also run it "lein ring server 8080" or xx "server-headless", doesn't open the browser
* 6) You need front-end project, this is just API, but you can view and test it as API (note 1)
* 7) Reconfigure front-end serverConfig.js to match URLs

(note 1) I've noticed that multipart file testing in my IDE is a bit different from how it's implemented in the frontend.


####### In more detail

clojure - java - ring - 

Simple frontend:
https://github.com/OlegPahhomov/dropbox-simple-front

Java spark backend:
https://github.com/OlegPahhomov/dropbox-java-spark-back


####### Clojure experience
Previously I had noted that clojure rocks, but plugins suck. So I made my own database plugin.
In java. Not bad :)

Not sure if I would use clojure/lein/ring over java/Spark. Debugging is tricky.