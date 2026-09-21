pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean package'
            }
        }

    }

    post {
        success {
            echo 'Spring Boot build SUCCESS'
        }

        failure {
            echo 'Spring Boot build FAILED'
        }
    }
}