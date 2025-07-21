
 pipeline {
     agent any

     tools {
         maven 'Maven 3.9.11' // Replace with your Jenkins Maven tool name
         jdk 'JDK 22'        // Replace with your Jenkins JDK tool name
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

