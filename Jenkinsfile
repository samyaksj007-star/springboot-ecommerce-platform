pipeline {
    agent any

    environment {
        DOCKER_COMPOSE_FILE = 'docker-compose.yml'
    }

    tools {
        maven 'Maven 3.8.1'  // configure in Jenkins -> Global Tool Config
    }

    stages {
        stage('Clone') {
            steps {
                git branch: 'jenkins-test', url: 'https://github.com/samyaksj007-star/springboot-ecommerce-platform.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Docker Compose Down (Clean old)') {
            steps {
                sh 'docker compose down'
            }
        }

        stage('Build Docker Images') {
            steps {
                sh 'docker compose build'
            }
        }

        stage('Deploy') {
            steps {
                sh 'docker compose up -d'
            }
        }
    }

    post {
        success {
            echo ' Deployment successful!'
        }
        failure {
            echo ' Build failed!'
        }
    }
}