pipeline {
    agent any

    tools {
        maven 'mvn3.9.11' // Replace with your Jenkins Maven tool name
        jdk 'temurin-8'   // Use your Java 8 JDK tool name instead of openjdk-22
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout code from Git
                git branch: 'main', url: 'https://github.com/paranjyothi-bandigari/RestAssured_APIAutomation_ChatGPT.git'
            }
        }

        stage('Build & Test') {
            steps {
                // Check Java version to confirm it's picking Java 8
                sh 'java -version'

                // Clean and run tests
                sh 'mvn clean test'
            }
        }
    }

    post {
        always {
            // Publish test reports
            junit '**/target/surefire-reports/*.xml'
        }
    }
}