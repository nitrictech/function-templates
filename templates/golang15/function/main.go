package main

import "github.com/nitrictech/go-sdk/v1/faas"

// NitricFunction - Handles individual function requests (http, events, etc.)
func NitricFunction(request *faas.NitricRequest) *faas.NitricResponse {
	// Do something interesting...
	return &faas.NitricResponse{
		Status: 200,
		Body:   []byte("Hello Nitric"),
	}
}

func main() {
	faas.Start(NitricFunction)
}
