<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/github_username/repo_name">
    <img src="https://cdn-icons-png.freepik.com/512/6020/6020329.png" alt="Logo" width="80" height="80">
  </a>

<h3 align="center">Employees Analyzer</h3>

  <p align="center">
    Application that identifies the pair of employees who have worked together on common projects for the longest period of time.
  </p>
</div>

## Used technologies

* [![Intellij][Intellij]][Intellij-url]
* [![Java][Java]][Java-url]
* [![Maven][Maven]][Maven-url]
* [![Sonar][Sonar]][Sonar-url]
* [![Git][Git]][Git-url]

## Hot to use it

* Clone the repository.
   ```sh
   git clone https://github.com/vasilev02/Valentin-Vasilev-employees
   ```
* Run this command which will produce two jar files(artifacts). The name of the jars depends on the build configuration in the pom file, artifactId and name of project.
   ```sh
   mvn assembly:single
   ```
* To start the jar use this command. If you navigate to the absolute path you can double click it and it will start.
   ```sh
   java -jar employees-1.0-SNAPSHOT-jar-with-dependencies.jar
   ```
* Upload CSV file and see the result.

## Functionallity
The user picks up a file from the file system and, after selecting it, all common projects of the pair who have worked together on common projects for the longest period of time are displayed in a brief information with datagrid.
We support different Date formats. The calculation is being computed in days.

## User interface
UI is simple structure from Java Swing API. It contains button for uploading csv files which listens all the time for interact with files. After we upload file
we will see information about longest working pair in two containers. First container inludes all common projects of the pair are displayed in datagrid with the following 
columns: `Employee ID #1, Employee ID #2, Project ID, Days worked`. Second is text area with infomration about the employees id, total count projects, respectively
total days count calculated in days. There is a info also about the weeks, months and years if there are employees working for a very long time. The last one shows
every project with it's id and worked days related to it.

## Error handling
For this part we have simple error handling coded. If you try to upload CSV file which is empty, with invalid dates or invalid structure like row without employee key
or without either start date or end date it will throw exception with the related message.

## Connect with me

[<img align="left" alt="linkedin" width="30px" src="https://cdn.icon-icons.com/icons2/2429/PNG/512/linkedin_logo_icon_147268.png" />][linkedin]

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[linkedin]: https://www.linkedin.com/in/valentin-vasilev-849a8b1a6/
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/in/valentin-vasilev-849a8b1a6/
[product-screenshot]: images/screenshot.png
[Hibernate]: https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white
[Hibernate-url]: https://hibernate.org
[Intellij]: https://img.shields.io/badge/IntelliJ_IDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white
[Intellij-url]: https://www.jetbrains.com/idea/
[Java]: https://img.shields.io/badge/OpenJDK-ED8B00?style=for-the-badge&logo=java&logoColor=white
[Java-url]: https://openjdk.org
[Maven]: https://img.shields.io/badge/apache_maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white
[Maven-url]: https://maven.apache.org
[Sonar]: https://img.shields.io/badge/Sonarqube-5190cf?style=for-the-badge&logo=sonarqube&logoColor=white
[Sonar-url]: https://www.jetbrains.com/qodana/?source=google&medium=cpc&campaign=19640357518&term=sonarqube&content=646717405636&gclid=Cj0KCQjw6KunBhDxARIsAKFUGs8hXIrh0cjYajUIehst74tV2S1JTXCm2uXbUT67mpZGRGC1Yoe0z9IaAkiTEALw_wcB
[Git]: https://img.shields.io/badge/GIT-E44C30?style=for-the-badge&logo=git&logoColor=white
[Git-url]: https://git-scm.com
