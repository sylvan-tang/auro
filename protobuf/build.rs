// Use this in build.rs
protobuf_codegen::Codegen::new()
    // Use `protoc` parser, optional.
    .protoc()
    // Use `protoc-bin-vendored` bundled protoc command, optional.
    .protoc_path(&protoc_bin_vendored::protoc_bin_path().unwrap())
    // All inputs and imports from the inputs must reside in `includes` directories.
    .includes(&["apis"])
    // Inputs must reside in some of include paths.
    .input("apis/assoc/v1/assoc_service.proto")
    .input("apis/echo/v1/greeting_service.proto")
    // Specify output directory relative to Cargo output directory.
    .cargo_out_dir("../rust/apis")
    .run_from_script();
