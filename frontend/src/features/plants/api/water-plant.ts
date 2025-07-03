import { useMutation, useQueryClient } from '@tanstack/react-query';
import { z } from 'zod';

import { api } from '@/lib/api-client';
import { MutationConfig } from '@/lib/react-query';
import { Plant } from '@/types/api';

import { getPlantsQueryOptions } from './get-plants';

export const waterPlantInputSchema = z.object({
  id: z.string().min(1, 'Required'),
});

export type WaterPlantInput = z.infer<typeof waterPlantInputSchema>;

export const waterPlant = ({
  data,
}: {
  data: WaterPlantInput;
}): Promise<Plant> => {
  return api.put(`/plants/water/${data.id}`);
};

type UseWaterPlantOptions = {
  mutationConfig?: MutationConfig<typeof waterPlant>;
};

export const useWaterPlant = ({
  mutationConfig,
}: UseWaterPlantOptions = {}) => {
  const queryClient = useQueryClient();

  const { onSuccess, ...restConfig } = mutationConfig || {};

  return useMutation({
    onSuccess: (...args) => {
      queryClient.invalidateQueries({
        queryKey: getPlantsQueryOptions().queryKey,
      });
      onSuccess?.(...args);
    },
    ...restConfig,
    mutationFn: waterPlant,
  });
};
