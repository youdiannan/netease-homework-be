package com.netease.contentsalesystem.controller;

import com.netease.contentsalesystem.entity.AccountItem;
import com.netease.contentsalesystem.entity.User;
import com.netease.contentsalesystem.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

import static com.netease.contentsalesystem.constant.Const.CURRENT_USER;

@RestController
public class AccountController {

    @Autowired
    private IAccountService accountService;

    @GetMapping("/api/account")
    public List<AccountItem> list(HttpSession session) {
        User user = (User) session.getAttribute(CURRENT_USER);
        return accountService.list(user.getId());
    }
}
