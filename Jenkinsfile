pipeline {
    agent any

    tools {
        jdk 'JDK21'
        maven 'Maven'
    }

    environment {
        EC2_USER = 'ec2-user'
        EC2_HOST = 'test'
        APP_DIR  = '/home/ec2-user/app'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Deploy to EC2') {
    steps {
        sshagent(credentials: ['ec2-ssh-key']) {
 bat '''
            ssh -o StrictHostKeyChecking=no %EC2_USER%@%EC2_HOST% "mkdir -p %APP_DIR%"
            '''

            bat '''
            scp -o StrictHostKeyChecking=no target/psp-gateway.jar %EC2_USER%@%EC2_HOST%:%APP_DIR%/app.jar
            '''

            bat '''
            ssh -o StrictHostKeyChecking=no %EC2_USER%@%EC2_HOST% "nohup java -jar %APP_DIR%/app.jar > %APP_DIR%/app.log 2>&1 < /dev/null &"
            '''
        }
    }
}
    }
}