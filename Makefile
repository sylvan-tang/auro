.PHONY: clean
## clean compiled binary file
clean:
	@printf "💧 \033[1;32m===> Cleaning bin binaries...\033[0m\n"
	@printf "\033[33mGit Commit:\t%s\nGit Version:\t%s\033[0m\n" $(GIT_COMMIT) $(GIT_VERSION)
	find . -name '*_gen.go' -type f -print -delete
	find . -name '*_service.proto' -type f -print -delete
