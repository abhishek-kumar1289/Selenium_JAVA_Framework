pipeline{
	agent any
	
	tools{
		maven 'Maven-3.9.10'
	}
	
	stages{
		
		stage('Checkout'){
			steps{
				git branch: 'main' , url:'https://github.com/abhishek-kumar1289/Selenium_JAVA_Framework.git'
			}
		}
		stage('Build'){
			steps{
				bat 'mvn clean install'
			}
		}
		stage('Test'){
			steps{
				bat 'mvn test'
			}
		}
		stage('Reports'){
			steps{
				publishHTML(target:[
					reportDir:'src/test/resources/ExtentReport',
					reportFiles: 'ExtentReport.html',
					reportName: 'Extent Spark Report'
				])
			}
		}
	}	
	
	post{
		always{
			archiveArtifacts artifacts: '**src/test/resources/ExtentReport/*.html', fingerprint:true
			junit 'target/surefire-reports/*.xml'
		}
	}
	
	
}