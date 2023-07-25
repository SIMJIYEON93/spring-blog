package com.sparta.springblog.controller;

/*
@PostMapping("/products")
public ProductResponseDto createProduct(@RequestBody ProductRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
    // 측정 시작 시간
    long startTime = System.currentTimeMillis();

    try {
        // 응답 보내기
        return productService.createProduct(requestDto, userDetails.getUser());
    } finally {
        // 측정 종료 시간
        long endTime = System.currentTimeMillis();
        // 수행시간 = 종료 시간 - 시작 시간
        long runTime = endTime - startTime;

        // 로그인 회원 정보
        User loginUser = userDetails.getUser();

        // API 사용시간 및 DB 에 기록
        ApiUseTime apiUseTime = apiUseTimeRepository.findByUser(loginUser)
                .orElse(null);
        if (apiUseTime == null) {
            // 로그인 회원의 기록이 없으면
            apiUseTime = new ApiUseTime(loginUser, runTime);
        } else {
            // 로그인 회원의 기록이 이미 있으면
            apiUseTime.addUseTime(runTime);
        }

        System.out.println("[API Use Time] Username: " + loginUser.getUsername() + ", Total Time: " + apiUseTime.getTotalTime() + " ms");
        apiUseTimeRepository.save(apiUseTime);
    }
}
* */