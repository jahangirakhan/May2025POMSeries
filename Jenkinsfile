pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    environment {
        TEST_REPO = 'https://github.com/jahangirakhan/May2025POMSeries'
    }

    stages {
        stage('Build') {
            steps {
                git 'https://github.com/jglick/simple-maven-project-with-tests.git'
                bat 'mvn clean install'
            }
            post {
                success {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }

        stage('Deploy to QA') {
            steps {
                echo "Deploying to QA environment..."
            }
        }

        stage('Checkout Test Repo') {
            steps {
                git "${env.TEST_REPO}"
            }
        }

        stage('Regression UI Automation Tests') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    bat 'mvn clean test -Dsurefire.suiteXmlFiles=src\\test\\resources\\testrunners\\testng_regression.xml'
                }
            }
        }

        stage('Publish Allure Reports') {
            steps {
                script {
                    allure([
                        includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: 'target/allure-results']]
                    ])
                }
            }
        }

        stage('Publish Extent Report') {
            steps {
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: false,
                    keepAll: true,
                    reportDir: 'reports',
                    reportFiles: 'TestExecutionReport.html',
                    reportName: 'HTML Regression Extent Report',
                    reportTitles: ''
                ])
            }
        }

        stage('Deploy to Stage') {
            steps {
                echo "Deploying to Stage environment..."
            }
        }

        stage('Sanity Automation Test') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    bat 'mvn clean test -Dsurefire.suiteXmlFiles=src\\test\\resources\\testrunners\\testng_sanity.xml -Denv=stage'
                }
            }
        }

        stage('Publish Sanity Extent Report') {
            steps {
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: false,
                    keepAll: true,
                    reportDir: 'reports',
                    reportFiles: 'TestExecutionReport.html',
                    reportName: 'HTML Sanity Extent Report',
                    reportTitles: ''
                ])
            }
        }

        stage('Deploy to PROD') {
            steps {
                echo "Deploying to PROD environment..."
            }
        }
    }
}
