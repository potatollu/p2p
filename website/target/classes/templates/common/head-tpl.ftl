<div class="el-header" >
		<div class="container" style="position: relative;">
			<ul class="nav navbar-nav navbar-right">
				<li><a href="/">首页</a></li>
					<#if logininfo??>
					    <li>
                            <a class="el-current-user" href="/personal.do">用户:${logininfo.username}</a>
                        </li>
					<#else>
						<li><a href="/login.html">登录</a></li>

						<li><a href="/register.html">快速注册</a></li>
					</#if>
					<li><a  href="/recharge.do">账户充值  </a></li>
					<li><a  href="/logout.do">注销 </a></li>
				<li><a href="/message.do">站内消息${count!''}</a></li>
				<li><a href="#">帮助</a></li>
			</ul>
		</div>
</div>
