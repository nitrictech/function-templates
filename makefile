test: test-golang test-java test-javascript test-typescript test-php test-python

test-golang:
	@echo Testing golang template build
	@docker build ./templates/golang15/template -f ./templates/golang15/Dockerfile

test-java:
	@echo Testing java template build
	@docker build ./templates/java11/template -f ./templates/java11/Dockerfile

test-javascript:
	@echo Testing golang template build
	@docker build ./templates/nodejs12/template -f ./templates/nodejs12/Dockerfile

test-typescript:
	@echo Testing golang template build
	@docker build ./templates/typescript/template -f ./templates/typescript/Dockerfile

test-php:
	@echo Testing golang template build
	@docker build ./templates/php8/template -f ./templates/php8/Dockerfile

test-python:
	@echo Testing golang template build
	@docker build ./templates/python37/template -f ./templates/python37/Dockerfile

