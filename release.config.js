var publishCmd = `
./gradlew assemble -Pversion=\${nextRelease.version} --parallel || exit 1
git tag -a -f \${nextRelease.version} \${nextRelease.version} -F CHANGELOG.md || exit 2
git push --force origin \${nextRelease.version} || exit 3
`
var config = require('semantic-release-preconfigured-conventional-commits');
config.plugins.push(
    ["@semantic-release/exec", {
        "publishCmd": publishCmd,
    }],
    ["@semantic-release/github", {
        "assets": [
            { "path": "DossierService/build/libs/*.jar"},
            { "path": "DrivingService/build/libs/*.jar"},
            { "path": "SystemTester/build/libs/*.jar"}
        ]
    }],
    "@semantic-release/git",
)
config.branches = ['main', 'automatic-release']

module.exports = config