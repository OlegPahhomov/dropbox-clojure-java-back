(defproject dropbox-clojure-java-back "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"

  :source-paths ["src/main/clojure"]
  :java-source-paths ["src/main/java"]

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/data.json "0.2.6"]
                 [compojure "1.3.1"]
                 [yesql "0.4.2"]
                 [cheshire "5.5.0"]
                 [org.postgresql/postgresql "9.4-1201-jdbc41"]
                 [ring/ring-defaults "0.1.2"]
                 [ring-cors "0.1.7"]
                 [org.flywaydb/flyway-core "3.1"]
                 [commons-dbutils/commons-dbutils "1.6"]
                 [ring/ring-json "0.4.0"]]
  :plugins [[lein-ring "0.8.13"]
            [com.github.metaphor/lein-flyway "1.0"]]

  :ring {:handler dropbox.handler/app}

  ;; Flyway Database Migration configuration
  :flyway {
           :driver   "org.postgresql.Driver"
           :url      "jdbc:postgresql://localhost:5432/"
           :user     "postgres"
           :password "12345"}

  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}}

  )


