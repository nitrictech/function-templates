package main

import "github.com/nitrictech/go-sdk/faas"

// NitricFunction - Handles individual function requests (http, events, etc.)
func NitricFunction(trigger *faas.NitricTrigger) *faas.NitricResponse {
	// Do something interesting...
	response := trigger.DefaultResponse()
	response.SetData([]byte("Hello Nitric"))

	return response
}

func main() {
	faas.Start(NitricFunction)
}
