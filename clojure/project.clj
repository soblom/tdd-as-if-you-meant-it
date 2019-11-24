(defproject tdd-clojure "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [clj-kondo "2019.11.03"]]
  :aliases {"clj-kondo" ["run" "-m" "clj-kondo.main"]}
  :plugins [[lein-kibit "0.1.7"]
            [lein-marginalia "0.9.1"]
            [lein-codox "0.10.7"]]
  :repl-options {:init-ns tdd-clojure.core})
