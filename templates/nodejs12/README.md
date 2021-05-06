# Template: Node JS

## Usage

All function code will be copied by the Nitric `build` command into `./function`. The `function` directory is expected to act as a [Node Module](https://nodejs.org/api/modules.html) which will start a http entrypoint for communication with the [Nitric Membrane](https://github.com/nitrictech/membrane). 

The Nitric [Node SDK](https://github.com/nitrictech/node-sdk) provides a function that exposes a function as a microservice which is included in the template code by default (see below example).

### Entrypoint Example

```javascript
const { faas } = require("@nitric/sdk");

// Start your function here
faas.start((request) => {
  return "Nitric NodeJS 12 Template - Success";
});
```
