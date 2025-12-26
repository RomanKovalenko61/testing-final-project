.PHONY: help clean compile test-web test-mobile

# Цвета для вывода
GREEN := \033[0;32m
YELLOW := \033[1;33m
NC := \033[0m # No Color

help: ## Показать доступные команды
	@echo "$(GREEN)Доступные команды:$(NC)"
	@echo ""
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | awk 'BEGIN {FS = ":.*?## "}; {printf "  $(YELLOW)%-15s$(NC) %s\n", $$1, $$2}'
	@echo ""

clean: ## Очистить проект
	@echo "$(GREEN)Очистка проекта...$(NC)"
	mvn clean

compile: ## Собрать проект
	@echo "$(GREEN)Сборка проекта...$(NC)"
	mvn clean compile test-compile

test-web: ## Запустить WEB тесты
	@echo "$(GREEN)Запуск WEB тестов...$(NC)"
	mvn clean test -Dsurefire.suiteXmlFiles=testng.xml

test-mobile: ## Запустить MOBILE тесты
	@echo "$(GREEN)Запуск MOBILE тестов...$(NC)"
	mvn clean test -Dsurefire.suiteXmlFiles=testng-mobile.xml

.DEFAULT_GOAL := help



