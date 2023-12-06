pipeline {
    agent any
    options {
        // Timeout counter starts AFTER agent is allocated
        timeout(time: 60, unit: 'SECONDS')
    }
    tools {
        jdk 'jdk-17'
    }

    stages {
        stage('Path') {
            steps {
                echo "Path: $PATH"
                sh 'whoami'
            }
        }
        stage('Java 17') {
            steps {
                echo "Path: $PATH"
                echo "Java Home: $JAVA_HOME"

                sh 'ls -l $JAVA_HOME/bin/java'
                sh 'which java'
                sh 'java -version'
                sh '$JAVA_HOME/bin/java -version'
            }
        }
    }
}
