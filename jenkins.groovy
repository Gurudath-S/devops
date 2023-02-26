pipeline {
    agent any

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