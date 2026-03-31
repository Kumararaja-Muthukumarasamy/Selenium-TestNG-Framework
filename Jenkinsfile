pipeline {
   
       parameters {
        choice(name: 'BROWSER', choices: ['chrome', 'firefox', 'edge'], description: 'Select browser')
        choice(name: 'ENV', choices: ['qa', 'uat', 'prod'], description: 'Select environment')
    }

    stages {

        stage('Checkout Code') {
            steps {
                git 'https://github.com/Kumararaja-Muthukumarasamy/Selenium-TestNG-Framework.git'
            }
        }

        stage('Clean & Build') {
            steps {
                bat 'mvn clean'
            }
        }

        stage('Run Tests') {
            steps {
                bat "mvn test -Dbrowser=%BROWSER% -Denv=%ENV%"
            }
        }

        stage('Publish Report') {
            steps {
                publishHTML([
                    reportDir: 'reports',
                    reportFiles: 'extent*.html',
                    reportName: 'Automation Report',
                    keepAll: true,
                    alwaysLinkToLastBuild: true,
                    allowMissing: true
                ])
            }
        }
    }

    post {
        always {
            script {
                // Ensure workspace context
                if (fileExists('reports')) {
                    archiveArtifacts artifacts: 'reports/*.html', fingerprint: true
                }
            }
        }

        success {
            echo '✅ Build Successful'
        }

        failure {
            echo '❌ Build Failed'
        }
    }
}