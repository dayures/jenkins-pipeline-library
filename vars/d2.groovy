
def startCluster( name, port, channel ) {
  sh "d2 cluster --channel ${channel} --port ${port} up --update ${name}"
  
  wait("$port")
}

def startClusterAndSeed( name, port, channel ) {
    sh "d2 cluster --channel ${channel} --port ${port} up --update ${name} --seed"
    
    wait("$port")
}

def startClusterAndSpecificSeed( name, port, channel, seedFile ) {
    sh "d2 cluster --channel ${channel} --port ${port} up --update ${name} --seed --seedFile{seedFile}"
    
    wait("$port")
}

def stopCluster( name ) {
    sh "d2 cluster down --clean ${name}"
}

def wait(port) {
   def response = "not_started"
    
    while (response != "200") {
        echo "status: $response"
        sleep(5) 
        response = ["curl", "-k", "-L", "-X", "GET", "-w", "%{http_code}", "--silent", "-o /dev/null", "http://localhost:${port}"].execute().text
    }
}
