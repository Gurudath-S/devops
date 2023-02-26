pipeline {
    agent any
  checkout([$class: 'GitSCM', 
  branches: [[name: '*/main']],
  userRemoteConfigs: [[url: 'https://github.com/Gurudath-S/devops.git']],
  extensions: [[$class: 'CredentialsBinding', credentialsId: '42a91fb6-36b6-4b2d-9ef9-5cd6b282c085', gitTool: 'Default']]])

    tools {
    git 'localgit'
}   
    stages {
        stage('Fetch Code') {
            steps {
                git 'https://github.com/Gurudath-S/devops.git'
            }
        }
        
        stage('Build and Test') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/main']], userRemoteConfigs: [[url: 'https://github.com/Gurudath-S/devops.git']]])
                sh 'mvn clean install'
            }
        }
        
        stage('Deploy to Tomcat') {
            steps {
                sh '/opt/tomcat/bin/shutdown.sh'
                sh 'rm -rf /opt/tomcat/webapps/devops*'
                sh 'cp target/devops.war /opt/tomcat/webapps/'
                sh '/opt/tomcat/bin/startup.sh'
            }
 }
}
}