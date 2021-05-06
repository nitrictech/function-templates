const { faas } = require("@nitric/sdk");

// Start your function here
faas.start((request) => {
  return "Nitric NodeJS 12 Template - Success";
});