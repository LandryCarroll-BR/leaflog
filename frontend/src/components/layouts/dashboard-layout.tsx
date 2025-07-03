import { Home } from 'lucide-react';
import { useEffect, useState } from 'react';
import { NavLink, useNavigation } from 'react-router';

import logo from '@/assets/logo.svg';
import { Navbar, NavbarItem } from '@/components/catalyst/navbar';
import { StackedLayout } from '@/components/catalyst/stacked-layout';
import { paths } from '@/config/paths';
import { cn } from '@/utils/cn';

import { Link } from '../ui/link';

type SideNavigationItem = {
  name: string;
  to: string;
  icon: (props: React.SVGProps<SVGSVGElement>) => JSX.Element;
};

const Logo = () => {
  return (
    <Link className="flex items-center text-black" to={paths.home.getHref()}>
      <img className="h-8 w-auto" src={logo} alt="Workflow" />
      <span className="text-sm font-semibold text-black">Leaf Log</span>
    </Link>
  );
};

const Progress = () => {
  const { state, location } = useNavigation();

  const [progress, setProgress] = useState(0);

  useEffect(() => {
    setProgress(0);
  }, [location?.pathname]);

  useEffect(() => {
    if (state === 'loading') {
      const timer = setInterval(() => {
        setProgress((oldProgress) => {
          if (oldProgress === 100) {
            clearInterval(timer);
            return 100;
          }
          const newProgress = oldProgress + 10;
          return newProgress > 100 ? 100 : newProgress;
        });
      }, 300);

      return () => {
        clearInterval(timer);
      };
    }
  }, [state]);

  if (state !== 'loading') {
    return null;
  }

  return (
    <div
      className="fixed left-0 top-0 h-1 bg-blue-500 transition-all duration-200 ease-in-out"
      style={{ width: `${progress}%` }}
    ></div>
  );
};

export function DashboardLayout({ children }: { children: React.ReactNode }) {
  const navigation = [
    { name: 'Dashboard', to: paths.app.dashboard.getHref(), icon: Home },
  ].filter(Boolean) as SideNavigationItem[];

  return (
    <>
      <StackedLayout
        navbar={
          <Navbar>
            <div className="flex shrink-0 items-center px-4">
              <Logo />
            </div>
            {navigation.map((item) => (
              <NavbarItem
                key={item.to}
                href={item.to}
                current={location.pathname === item.to}
              >
                {item.name}
              </NavbarItem>
            ))}
          </Navbar>
        }
        sidebar={
          <div className="flex flex-col gap-2 p-4">
            {navigation.map((item) => (
              <NavLink
                key={item.name}
                to={item.to}
                end={item.name !== 'Discussions'}
                className={({ isActive }) =>
                  cn(
                    'text-gray-700 hover:bg-gray-100',
                    'group flex items-center rounded-md p-2 text-base font-medium',
                    isActive && 'bg-gray-200 text-gray-900',
                  )
                }
              >
                <item.icon
                  className={cn(
                    'text-gray-500 group-hover:text-gray-700',
                    'mr-4 size-6 shrink-0',
                  )}
                  aria-hidden="true"
                />
                {item.name}
              </NavLink>
            ))}
          </div>
        }
      >
        {children}
      </StackedLayout>
    </>
  );
}
