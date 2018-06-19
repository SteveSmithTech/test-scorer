# A rough-and-ready parser for cucumber-js json output

For results produced by https://github.com/cucumber/cucumber-js, this parser can generate test stability reports at
the _scenario_ level

We map the scenario steps to a test status as
* all passed = SUCCESS
* any failed = FAILED
* any skipped = IGNORED
* any ambiguous or undefined = ERROR

We identify tests as `{Feature}`::`{Scenario name}` when we map them to the test-scorer `TestReport` domain.

### And example of a report file
```json
[
  {
    "description": "A description of feature1 here",
    "keyword": "Feature",
    "line": 3,
    "name": "An example feature",
    "tags": [
      {
        "line": 1,
        "name": "@example1"
      },
      {
        "line": 1,
        "name": "@example2"
      }
    ],
    "uri": "/some/path/here",
    "elements": [
      {
        "keyword": "Scenario",
        "line": 10,
        "name": "An example Scenario",
        "tags": [
          {
            "line": 1,
            "name": "@tag1"
          },
          {
            "line": 1,
            "name": "@tag2"
          }
        ],
        "id": "some-id-for-a-thing",
        "steps": [
          {
            "arguments": [],
            "keyword": "Before",
            "result": {
              "status": "passed",
              "duration": 1
            },
            "hidden": true,
            "match": {
              "location": "/path/to/thing/apickli/apickli-gherkin.js:30"
            }
          },
          {
            "arguments": [],
            "keyword": "And",
            "name": "some-and-step-name",
            "result": {
              "status": "passed",
              "duration": 1
            },
            "hidden": true,
            "match": {
              "location": "/path/to/something"
            }
          },
          {
            "arguments": [],
            "keyword": "Then ",
            "name": "some-then-step-name",
            "result": {
              "status": "failed",
              "duration": 2,
              "error_message": "Some error message"
            },
            "line": 13,
            "match": {
              "location": "/path/to/somewhere/common-steps.js:157"
            }
          }
        ]
      },
      {
        "keyword": "Scenario2",
        "line": 10,
        "name": "An second example Scenario",
        "tags": [],
        "id": "some-id-for-a-scenario-2",
        "steps": [
          {
            "arguments": [],
            "keyword": "Before",
            "result": {
              "status": "passed",
              "duration": 1
            },
            "hidden": true,
            "match": {
              "location": "/path/to/thing/apickli/apickli-gherkin.js:30"
            }
          },
          {
            "arguments": [],
            "keyword": "And",
            "name": "some-and-step-name",
            "result": {
              "status": "passed",
              "duration": 1
            },
            "hidden": true,
            "match": {
              "location": "/path/to/something"
            }
          },
          {
            "arguments": [],
            "keyword": "Then ",
            "name": "some-then-step-name",
            "result": {
              "status": "passed",
              "duration": 2
            },
            "line": 13,
            "match": {
              "location": "/path/to/somewhere/common-steps.js:157"
            }
          }
        ]
      }
    ]
  },
  {
    "description": "A description of feature2 here",
    "keyword": "Feature",
    "line": 3,
    "name": "An second example feature",
    "tags": [],
    "uri": "/some/path/here",
    "elements": [
      {
        "keyword": "Scenario1",
        "line": 10,
        "name": "An example Scenario",
        "tags": [
          {
            "line": 1,
            "name": "@tag1"
          },
          {
            "line": 1,
            "name": "@tag2"
          }
        ],
        "id": "some-id-for-a-thing-21",
        "steps": [
          {
            "arguments": [],
            "keyword": "Before",
            "result": {
              "status": "passed",
              "duration": 1
            },
            "hidden": true,
            "match": {
              "location": "/path/to/thing/apickli/apickli-gherkin.js:30"
            }
          },
          {
            "arguments": [],
            "keyword": "And",
            "name": "some-and-step-name",
            "result": {
              "status": "passed",
              "duration": 1
            },
            "hidden": true,
            "match": {
              "location": "/path/to/something"
            }
          },
          {
            "arguments": [],
            "keyword": "Then ",
            "name": "some-then-step-name",
            "result": {
              "status": "failed",
              "duration": 2,
              "error_message": "Some error message"
            },
            "line": 13,
            "match": {
              "location": "/path/to/somewhere/common-steps.js:157"
            }
          }
        ]
      },
      {
        "keyword": "Scenario2",
        "line": 10,
        "name": "An second example Scenario",
        "tags": [],
        "id": "some-id-for-a-scenario-22",
        "steps": [
          {
            "arguments": [],
            "keyword": "Before",
            "result": {
              "status": "passed",
              "duration": 1
            },
            "hidden": true,
            "match": {
              "location": "/path/to/thing/apickli/apickli-gherkin.js:30"
            }
          },
          {
            "arguments": [],
            "keyword": "And",
            "name": "some-and-step-name",
            "result": {
              "status": "passed",
              "duration": 1
            },
            "hidden": true,
            "match": {
              "location": "/path/to/something"
            }
          },
          {
            "arguments": [],
            "keyword": "Then ",
            "name": "some-then-step-name",
            "result": {
              "status": "passed",
              "duration": 2
            },
            "line": 13,
            "match": {
              "location": "/path/to/somewhere/common-steps.js:157"
            }
          }
        ]
      }
    ]
  }
]
```