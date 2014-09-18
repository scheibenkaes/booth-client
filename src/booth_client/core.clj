(ns booth-client.core
  (:require [clojure.core.async :refer [chan go-loop <!! >!!]]
            [clojure.tools.cli :refer [parse-opts]]
            [clojure.java.io :as io]
            [clojure.java.shell :as shell]
            )
  (:gen-class))

(def cli-options
  [["-c" "--command COMMAND" "Command to run"
    :id :command
    :default "echo running"]
   ])

(def notification-received-chan (chan))

(defn start-listening-for-notifications [notify-chan]
  (let [reader (-> *in* io/reader)]
    (go-loop []
             (try
               (let [data (.readLine reader)]
                 (when data
                   (>!! notify-chan data))
                 (Thread/yield))
               (catch Exception e
                 (println
                  (.getMessage e))))
             (recur)
             )))

(defn -main
  [& args]
  (let [{arguments :arguments
         {cmd :command} :options} (parse-opts args cli-options)]
    (println "Starting...")
    (go-loop []
             (let [data (<!! notification-received-chan)]
               (apply println "Running command " cmd arguments)
               (apply shell/sh (concat [cmd] arguments))
               (recur)))
    (<!!
     (start-listening-for-notifications notification-received-chan))))
