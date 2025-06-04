pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    parameters {
        string(name: 'BRANCH', defaultValue: 'main', description: 'Git branch to build')
        choice(name: 'ENV', choices: ['qa', 'stage', 'prod'], description: 'Deployment environment')
    }

    environment {
        TEST_REPO = 'https://github.com/jahangirakhan/May2025POMSeries'
    }

    stages {
        stage('Build') {
            steps {
                dir('app') {
                    git branch: "${params.BRANCH}", url: 'https://github.com/jglick/simple-maven-project-with-tests.git'
                    bat 'mvn clean install'
                }
            }
            post {
                success {
                    junit 'app/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts artifacts: 'app/target/*.jar', fingerprint: true
                }
            }
        }

        stage('Deploy to QA') {
            when {
                expression { params.ENV == 'qa' }
            }
            steps {
                echo "Deploying to QA environment..."
            }
        }

        stage('Checkout Test Repo') {
            steps {
                dir('ui-tests') {
                    git "${env.TEST_REPO}"
                }
            }
        }

        stage('Regression UI Automation Tests') {
            when {
                expression { params.ENV == 'qa' || params.ENV == 'stage' }
            }
            steps {
                dir('ui-tests') {
                    catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                        bat 'mvn clean test -Dsurefire.suiteXmlFiles=src\\test\\resources\\testrunners\\testng_regression.xml'
                    }
                }
            }
        }

        stage('Publish Allure Reports') {
            steps {
                dir('ui-tests') {
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
        }

        stage('Publish Extent Report') {
            steps {
                dir('ui-tests') {
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
        }

        stage('Deploy to Stage') {
            when {
                expression { params.ENV == 'stage' }
            }
            steps {
                echo "Deploying to Stage environment..."
            }
        }

        stage('Sanity Automation Test') {
            when {
                expression { params.ENV == 'stage' || params.ENV == 'prod' }
            }
            steps {
                dir('ui-tests') {
                    catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                        bat "mvn clean test -Dsurefire.suiteXmlFiles=src\\test\\resources\\testrunners\\testng_sanity.xml -Denv=${params.ENV}"
                    }
                }
            }
        }

        stage('Publish Sanity Extent Report') {
            when {
                expression { params.ENV == 'stage' || params.ENV == 'prod' }
            }
            steps {
                dir('ui-tests') {
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
        }

        stage('Deploy to PROD') {
            when {
                expression { params.ENV == 'prod' }
            }
            steps {
                echo "Deploying to PROD environment..."
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished.'
        }
        failure {
            echo 'Pipeline failed. Please check the logs!'
        }
        success {
            echo 'Pipeline succeeded!'
        }
    }
}
