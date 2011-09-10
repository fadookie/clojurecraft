;Default ScreenShell repl doesn't work here. Need to CTRL-D, cd /Users/eliot/Dev/clojure/clojurecraft and then run lein repl in ScreenShell

;=Setup=
(require '(clojurecraft [core :as core] [actions :as actions] [loops :as loops] [events :as events]))

;Create a bot and connect to localhost
(def bot (core/connect core/minecraft-local "bot"))

;Remote-style invocation to connect to any server on any port that doesn't require Mojang login
(def remote-bot (clojurecraft.core/connect {:name "localhost" :port 25565} "Hoboto Remote"))

;Quickly switch which bot is controlled by to commands below
(def bot remote-bot)

(def bot bot2)

;=Actions=

;Walk
(actions/perform! (actions/move bot 2 0 1))

;Jump
(actions/perform! (actions/jump bot))

;Chat
(actions/perform! (actions/chat bot "Hello world!"))

;Running leap
(actions/perform! (actions/jump bot))
(actions/perform! (actions/move bot -2 0 2))

;Respawn
(clojurecraft.actions/respawn bot) ;not working

;Disconnect
(clojurecraft.core/disconnect bot)

;=Event Handlers=
;These are not working in REPL :'(
(defn jump-on-chat [bot message]
  [(clojurecraft.actions/jump bot)])
(clojurecraft.events/add-handler bot :chat #'jump-on-chat)

;Function to check if message is from this bot
(defn message-is-own? [bot message]
  (clojure.contrib.string/substring? (str "<" (:username bot) ">")
                                     message))

;Death handler
(defn dead-handler [bot]
  [(clojurecraft.actions/chat bot "WHY U DO THIS?")
   (clojurecraft.actions/respawn bot)])
(clojurecraft.events/add-handler bot :dead #'dead-handler)

;=Loops=
(defn jump [bot]
  [(clojurecraft.actions/jump bot)])
(clojurecraft.loops/add-loop bot #'jump 3000 :jump-loop)

;Remove loop
(clojurecraft.loops/remove-loop bot :jump-loop)

;=Data Structures=
(:time (:world bot))

(:world bot)

(actions/perform! (actions/jump bot))
@(:player bot)

(:loc @(:player bot)) ;This map is actually a Ref to a map so we need to deref it with @ (reader macro for deref function)

(:holding @(:player bot))

;Documentation
(doc actions/move)
(doc actions/jump)

(doc clojurecraft.events/add-handler)

(find-doc "clojurecraft.chunks")
