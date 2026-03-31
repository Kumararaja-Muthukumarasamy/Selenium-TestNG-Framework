pipeline {
    agent any

    tools {
        maven 'Maven3'   // match your Jenkins config name
        jdk 'Java17'
    }

    parameters {
        choice(name: 'BROWSER', choices: ['chrome', 'firefox', 'edge'], description: 'Select browser')
        choice(name: 'ENV', choices: ['qa', 'uat', 'prod'], description: 'Select environment')
    }

    stages {

        stage('Checkout Code') {
            steps {
                git 'https://your-repo-url.git'
            }
        }

        stage('Clean & Build') {
            steps {
                bat 'mvn clean'
            }
        }

        stage('Run Tests') {
            steps {
                bat """
                mvn test \
                -Dbrowser=${params.BROWSER} \
                -Denv=${params.ENV}
                """
            }
        }

        stage('Publish Report') {
            steps {
                publishHTML([
                    reportDir: 'reports',
                    reportFiles: 'extent-*.html',
                    reportName: 'Automation Report',
                    keepAll: true,
                    alwaysLinkToLastBuild: true,
                    allowMissing: false
                ])
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'reports/*.html', fingerprint: true
        }

        success {
            echo '✅ Build Successful'
        }

        failure {
            echo '❌ Build Failed'
        }
    }
}