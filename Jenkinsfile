pipeline {
    agent any

    parameters {
        choice(name: 'BROWSER', choices: ['chrome', 'firefox', 'edge'], description: 'Select browser')
        choice(name: 'ENV', choices: ['qa', 'uat', 'prod'], description: 'Select environment')
    }

    stages {

        stage('Clean & Build') {
            steps {
                bat 'mvn clean'
            }
        }

        stage('Run Tests') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    bat "mvn test -Dbrowser=%BROWSER% -Denv=%ENV%"
                }
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
                if (fileExists('reports')) {
                    archiveArtifacts artifacts: 'reports/*.html', fingerprint: true
                }
            }
        }

        success {
            echo 'Build Successful'
        }

        failure {
            echo 'Build Failed'
        }
    }
}