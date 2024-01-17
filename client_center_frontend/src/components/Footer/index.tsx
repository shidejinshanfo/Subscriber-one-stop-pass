import { GithubOutlined } from '@ant-design/icons';
import { DefaultFooter } from '@ant-design/pro-components';
import { useIntl } from '@umijs/max';
import React from 'react';
import {BILIBILI_LINK} from "@/constant";

const Footer: React.FC = () => {
  const intl = useIntl();
  const defaultMessage = intl.formatMessage({
    id: 'app.copyright.produced',
    defaultMessage: '是 的 金 山 佛 出 品',
  });

  const currentYear = new Date().getFullYear();

  return (
    <DefaultFooter
      style={{
        background: 'none',
      }}
      copyright={`${currentYear} ${defaultMessage}`}
      links={[
        {
          key: 'Clinet center',
          title: '用户中心',
          href: 'http://localhost:8000/user/login',
          blankTarget: true,
        },
        {
          key: 'Shidejinshanfo',
          title: '是的金山佛',
          href: BILIBILI_LINK,
          blankTarget: true,
        },
        {
          key: 'github',
          title: <GithubOutlined />,
          href: 'https://github.com/shidejinshanfo',
          blankTarget: true,
        },

      ]}
    />
  );
};

export default Footer;
