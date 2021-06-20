import { faas } from "@nitric/sdk";

// Start your function here
faas.start(async (_: faas.NitricTrigger<string>): Promise<string> => {
  return "Hello from Typescript :)";
});
