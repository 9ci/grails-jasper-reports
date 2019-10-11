grails.useGrails3FolderLayout = true
//grails.plugin.location."view-tools" = "../grails-view-tools"
//grails.plugin.location."dynamic-jasper" = "./dynamic-jasper"

grails.servlet.version = "3.0" // Change depending on target container compliance (2.5 or 3.0)

grails.project.work.dir = ".grails"
grails.project.fork = [
    war: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
    console: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256]
]

grails.project.dependency.resolver = "maven" // or ivy
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        excludes "grails-docs" //remove itext from here http://stackoverflow.com/questions/23660018/grails-2-3-7-remove-itext-2-0-8-jar
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        mavenLocal()
        grailsCentral()
        mavenCentral()

        //needed to pick up the shit for the crazy itext version they use
        mavenRepo "http://jaspersoft.jfrog.io/jaspersoft/third-party-ce-artifacts/"
        mavenRepo (id:'9ci-artifactory', url:'http://repo.9ci.com/grails-plugins') {
            updatePolicy 'always' //this changes the default for updating SNAPSHOTs from interval:x
        }

    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.
        // runtime 'mysql:mysql-connector-java:5.1.27'
        //compile("com.lowagie:itext:2.1.7")
        //compile 'org.olap4j:olap4j:1.2.0'
        def poiVersion='3.9'
        compile 'org.apache.poi:poi:'+poiVersion
        compile 'org.apache.poi:poi-ooxml:'+poiVersion
        //compile 'org.apache.poi:poi-ooxml-schemas:'+poiVersion
        compile 'org.apache.poi:ooxml-schemas:1.3'

        compile('net.sf.jasperreports:jasperreports:6.3.1'){
            excludes 'commons-logging', 'olap4j',
               'jcommon','jfreechart' //<-charts
                //, 'itext'
        }
        //the DejaVu sans font which is the recomended pacaked font for Jasper reports and pdf exporting
        compile('net.sf.jasperreports:jasperreports-fonts:6.0.0')
        //compile('net.sf.jasperreports:jasperreports:6.3.1')

        //compile("org.eclipse.jdt.core.compiler:ecj:4.3.1")
        build('org.grails:grails-docs:2.5.5') { excludes 'itext' }

        compile('net.sourceforge.dynamicreports:dynamicreports-core:4.1.1'){
            excludes 'jasperreports',
                    'barcode4j','batik-bridge' //don't need the barcode stuff
        }
//        compile('net.sourceforge.dynamicreports:dynamicreports-adhoc:4.1.1'){
//            excludes 'jasperreports'
//        }

    }

    plugins {

        build(":tomcat:7.0.70"){
            excludes 'ecj' //<-- this needs to be excluded for java 8 and not to conflict with newer one in jasper
            export = false
        }
        compile(":hibernate4:4.3.10") {
            export = false
        }

        provided(":executor:0.3") {
            export = false
        }

        build(":release:3.1.2", ":rest-client-builder:2.1.1") { export = false }

        //provides the LoaderUrlViewResolver for JasperViewLoader
        compile(":view-tools:0.6-grails2-SNAPSHOT") {
            export = false
        }
    }
}
